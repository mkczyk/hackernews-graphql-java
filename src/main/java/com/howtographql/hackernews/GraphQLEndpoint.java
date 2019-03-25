package com.howtographql.hackernews;

import com.howtographql.hackernews.dao.LinkRepository;
import com.howtographql.hackernews.dao.UserRepository;
import com.howtographql.hackernews.dao.VoteRepository;
import com.howtographql.hackernews.dao.impl.*;
import com.howtographql.hackernews.graphql.AuthContext;
import com.howtographql.hackernews.graphql.Mutation;
import com.howtographql.hackernews.graphql.Query;
import com.howtographql.hackernews.graphql.SanitizedError;
import com.howtographql.hackernews.graphql.resolver.LinkResolver;
import com.howtographql.hackernews.graphql.resolver.SigninResolver;
import com.howtographql.hackernews.graphql.resolver.VoteResolver;
import com.howtographql.hackernews.graphql.type.User;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.schema.GraphQLSchema;
import graphql.servlet.GraphQLContext;
import graphql.servlet.SimpleGraphQLServlet;
import io.leangen.graphql.GraphQLSchemaGenerator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {
    private static boolean IN_MEMORY = true;

    private static final LinkRepository linkRepository;
    private static final UserRepository userRepository;
    private static final VoteRepository voteRepository;

    static {
        if(!IN_MEMORY) {
            // Change to `new MongoClient("mongodb://<host>:<port>/hackernews")`
            // if you don't have Mongo running locally on port 27017
            MongoDatabase mongo = new MongoClient().getDatabase("hackernews");
            linkRepository = new LinkRepositoryMongo(mongo.getCollection("links"));
            userRepository = new UserRepositoryMongo(mongo.getCollection("users"));
            voteRepository = new VoteRepositoryMongo(mongo.getCollection("votes"));
        } else {
            linkRepository = new LinkRepositoryInMemory();
            userRepository = new UserRepositoryInMemory();
            voteRepository = new VoteRepositoryInMemory();
        }
    }

    public GraphQLEndpoint() {
        super(buildSchema());
    }

    @Override
    protected GraphQLContext createContext(Optional<HttpServletRequest> request, Optional<HttpServletResponse> response) {
        User user = request
                .map(req -> req.getHeader("Authorization"))
                .filter(id -> !id.isEmpty())
                .map(id -> id.replace("Bearer ", ""))
                .map(userRepository::findById)
                .orElse(null);
        return new AuthContext(user, request, response);
    }

    @Override
    protected List<GraphQLError> filterGraphQLErrors(List<GraphQLError> errors) {
        return errors.stream()
                .filter(e -> e instanceof ExceptionWhileDataFetching || super.isClientError(e))
                .map(e -> e instanceof ExceptionWhileDataFetching ? new SanitizedError((ExceptionWhileDataFetching) e) : e)
                .collect(Collectors.toList());
    }

    private static GraphQLSchema buildSchema() {
        Query query = new Query(linkRepository);
        Mutation mutation = new Mutation(linkRepository, userRepository, voteRepository);
        LinkResolver linkResolver = new LinkResolver(userRepository);
        SigninResolver signinResolver = new SigninResolver();
        VoteResolver voteResolver = new VoteResolver(linkRepository, userRepository);

        return new GraphQLSchemaGenerator()
                .withOperationsFromSingletons(query, mutation,
                        linkResolver, signinResolver, voteResolver)
                .generate();
    }

}