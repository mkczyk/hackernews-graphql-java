# hackernews-graphql-java

Code basis on How to GraphQL [graphql-java Tutorial](https://www.howtographql.com/graphql-java/0-introduction/):
- Introduction
- Getting Started
- Queries
- Mutations
- Connectors
- Authentication (based on ID as they said)
- More Mutations
- Error Handling
- Subscriptions (not supported in graphql-java)
- Filtering
- Pagination
- Alternative approaches to schema development (not yet)

### Running
```
mvn jetty:run
```

### Changes compared to the tutorial

* Java packages
* Interfaces for repositories
* In memory repositories (in tutorial the last one in memory repository 
is in [Mutations](https://www.howtographql.com/graphql-java/3-mutations/) part)
* Files for [JS GraphQL](https://plugins.jetbrains.com/plugin/8097-js-graphql) IntelliJ plugin (version 2, 
currently [beta](https://github.com/jimkyndemeyer/js-graphql-intellij-plugin/releases/tag/2.0.0-beta-1)): 
`.graphqlconfig` and `scratch.graphql` 

### Working with Mongo

Application running with in memory repositories by default.

If you want to persist data, you have to run MongoDB, for example in Docker:

```
docker run --name some-mongo -p 27017:27017 -d mongo
``` 

