If you are a mobile developer, you understand the importance of a faster API and a more flexible API.

But API’s are designed for multiple clients each with different requirements and more often you will find that those API’s are not optimized for mobile apps. That is where newer technologies like GraphQL can help.

GraphQL is a query language for you API, that gives clients the power to ask for exactly what they need and nothing more. If you have not read about GraphQL explore more at <http://graphql.org/>.



###Recommended Reading

https://github.com/apollographql/apollo-android/blob/master/README.md



### **GraphQL on Android**

Let’s explore how we can consume GraphQL API’s on Android. Although there are multiple client libraries for the web, so far there is only one GraphQL client for Android [Apollo](https://github.com/apollographql/apollo-android).

[Apollo Android](https://github.com/apollographql/apollo-android) is an awesome GraphQL client that makes consuming GraphQL API easy. It has 2 main components

- Apollo Codegen, this component is a Gradle plugin to generate code like ButterKnife, Apollo Codegen generates Java models from standard GraphQL queries at compile time.
- Networking/Caching, the other component of Apollo android is the networking and caching piece, this takes care of all the network communication with your GraphQL API, parsing the response to correct model, enabling you to pass dynamic data to your GraphQL queries and response caching.





### Setting up the Toolchain and dependencies

Now we know what is GraphQL and how Apollo Android works let’s see how we can integrate Apollo in our android app.

Create an empty android project if you don’t already have one. 

####Project Level Changes

Now in your project level **build.gradle** file add this line

```groovy
classpath 'com.apollographql.apollo:apollo-gradle-plugin:0.5.0'
```

this should be placed after **com.android.tools.** Now open your app’s **build.gradle** and add this line on top

```groovy
apply plugin: 'com.apollographql.android'
```

this should go below **com.android.application,** if you want to use apollo for your Kotlin project, add apollo plugin before you kotlin plugin. With these 2 dependencies, we've added automatic code generation to our build toolchain.

####Module Level Changes

In your module-level **build.gradle** add the following two dependencies using the given lines:

```groovy
implementation 'com.apollographql.apollo:apollo-runtime:0.5.0'
implementation "com.apollographql.apollo:apollo-android-support:0.5.0"
```

These two decencies now allow you execute GraphQL queries and pulls in OKHttp for the web requests.





### Generating Code from Queries

Apollo takes your GraphQL queries, takes the schema and generate Java classes from it. Let’s explore how it works

- Create a folder under **src/main** on the same level as your **java/res** folder. You can name the folder anything, but I name it `graphql`.
- Add your **schema.json** file to this folder. Schema.json if the file that describes your GraphQL API, all fields, input arguments etc. Sample schema file [here](https://github.com/apollographql/apollo-android/blob/master/apollo-sample/src/main/graphql/com/apollographql/apollo/sample/schema.json)
- Add your GraphQL query file with a **.graphql extension** in the same folder, this queries will be then used by Apollo Codegen to generate the data model for the response. Sample GraphQL files [here](https://github.com/apollographql/apollo-android/blob/master/apollo-sample/src/main/graphql/com/apollographql/apollo/sample/GithuntFeedQuery.graphql)

After adding your schema and graphql files, rebuild the project. Apollo will parse the queries and schema and generate code for you. Once your build is complete, you can explore the generated files by going to **app/build/generated/source/apollo** folder.





### Wiring up

Now we have the Apollo dependencies added and codegen working, let’s wire up everything and make our first GraphQL network call.

In this sample application, Nosto's GraphQL endpoint at https://api.nosto.com/v1/graphql is being used. Accessing Nosto's APIs requires that each request use a basic-authentication header.



Apollo uses [OkHTTP](http://square.github.io/okhttp/) as its networking client. OkHttp requires that you create what are known as "interceptors". These interceptors can mutate outbound requests.

```java
OkHttpClient okHttpClient = new OkHttpClient.Builder()
        .addInterceptor(logging)
        .build();
```

Once Apollo client is built, the same client can be reused for all your network requests including non-GraphQL requests.

##### Adding Authentication Support

The given interceptor adds a username and password to all outgoing requests. Nosto does not use usernames as such and therefore and empty string is used as the username.

```java
import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BasicAuthInterceptor implements Interceptor {

    private final String credentials;

    public BasicAuthInterceptor(String username, String password) {
        this.credentials = Credentials.basic(username, password);
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder builder = original.newBuilder()
                .method(original.method(), original.body())
                .addHeader("Authorization", credentials);
        return chain.proceed(builder.build());
    }
}
```

#####Adding Debugging Support

When consuming Nosto's GraphQL API, it is important that your test queries and mutations do not affect the data in Nosto account.

For this reason, Nosto allows adding a header called `X-Nosto-Ignore` to requests. When this header is detected, the GraphQL endpoints work normally but the data is excluded from statistics, etc.

This interceptor simply checks whether the build is a debug one and if so, adds the aforementioned header.

```java
package com.nosto.graphql.request.interceptors;

import android.support.annotation.NonNull;

import com.nosto.graphql.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class DebugModeInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();
        if (BuildConfig.DEBUG) {
            Request.Builder builder = original.newBuilder()
                    .method(original.method(), original.body())
                    .addHeader("X-Nosto-Ignore", "True");
            return chain.proceed(builder.build());
        } else {
            return chain.proceed(original);
        }
    }
}
```



#####Adding Caching Support (Optional)

If you want to enable caching, Apollo comes with 3 level of cache, read more about caching here <https://github.com/apollographql/apollo-android>. Now we have the OkHttp Client and Cache, we can construct the Apollo client object.

```
apolloClient = ApolloClient.builder()
        .serverUrl(BASE_URL)
        .okHttpClient(okHttpClient)
        .normalizedCache(normalizedCacheFactory, cacheKeyResolver)
        .build();
```

