## Post List

Simple Android application that displays posts from [JSONPlaceholder](https://jsonplaceholder.typicode.com/).

### Import

There are two Android projects in this folder:

1. mvp/PostList
2. mvvm/PostList

Both projects are Android Studio ones. To build either project you must import it into Android Studio.

### Description

The application consists of a single screen. The screen displays the top posts from [JSONPlaceholder](https://jsonplaceholder.typicode.com/) in a list.
For each post in the list the following items are visible:

1. Sequence number
2. Post title
3. Username of the user that posted it

### Variants

There are two variants of the application:

1. A variant that utilises the MVP design pattern
2. A variant that utilises the MVVM design pattern

Both variants behave identically. They were chosen to demonstrate these two patterns.

Please note that the architecture chosen is too complex for the simple functionality required. However, it was chosen to illustrate
the design patterns and best practices followed in larger scale and more demanding applications. A reference to this point was made into
some comment sections in the code.
  
### Assumptions

The following assumptions were made about the posts and the users:

1. The ordering of the top posts changes. As such, the top N posts are not always the same.
2. The username of the users posting does not change.

These assumptions were arbitrary and underlined the data caching strategy adopted.

### Configuration

There are 3 external configuration parameters contained in the top-level `build.gradle` file:

1. `numberOfPosts`: The number of top posts that the application will show
2. `cacheExpirySecs`: The time in seconds that the post data are cached locally (to reduce the number of remote calls)
3. `apiBaseUrl`: The base URL of the [JSONPlaceholder](https://jsonplaceholder.typicode.com/) endpoints

### Libraries

The [Retrofit2](https://square.github.io/retrofit/) library was used to call the [JSONPlaceHolder](https://jsonplaceholder.typicode.com/) endpoints whereas [RxJava2](https://github.com/ReactiveX/RxJava) was employed to orchestrate the remote server calls
and local cache calls. The Android [Room](https://developer.android.com/training/data-storage/room/index.html) persistence library was
utilised for data caching. For dependency injection, the [Koin](https://insert-koin.io/) framework was used. To aid unit testing, the 
[Mockito](https://site.mockito.org/) mocking framework was included.

### Contact

Please e-mail <akm799@yahoo.com> for any questions or requests.