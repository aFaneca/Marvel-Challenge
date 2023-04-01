# Marvel Challenge

- [Screenshots](#screenshots)
- [Tech Overview](#tech-overview)
    - [Features implemented](#features-implemented)
    - [Building the project](#building-the-project)
    - [Arch](#arch)
    - [Relevant dependencies](#relevant-dependencies)
    - [UI](#ui)
    - [Offline-first & pagination](#offline-first--pagination)
        - [Offline Support](#offline-support)
        - [Pagination](#pagination)
    - [Unit tests](#unit-tests)
- [What Could Be Improved](#what-could-be-improved)

# Screenshots
You can find the full list [here](https://github.com/aFaneca/Marvel-Challenge/tree/main/screenshots).
|   Character list (light)   |   Character list (dark)   |   Search (dark)  | Search (empty)
|:-------------:|:-------------:|:-------------:|:-------------:|
| ![](https://github.com/aFaneca/Marvel-Challenge/blob/main/screenshots/list_light.png?raw=true)|![](https://github.com/aFaneca/Marvel-Challenge/blob/main/screenshots/list_dark.png?raw=true)|![](https://github.com/aFaneca/Marvel-Challenge/blob/main/screenshots/search_dark.png?raw=true)|![](https://github.com/aFaneca/Marvel-Challenge/blob/main/screenshots/list_empty_dark.png?raw=true)|

|   Character list (error)   |   Character list (top error)   |   About (dark)  | Details (light)
|:-------------:|:-------------:|:-------------:|:-------------:|
|![](https://github.com/aFaneca/Marvel-Challenge/blob/main/screenshots/list_error_dark.png?raw=true)|![](https://github.com/aFaneca/Marvel-Challenge/blob/main/screenshots/list_top_error_dark.png?raw=true)|![](https://github.com/aFaneca/Marvel-Challenge/blob/main/screenshots/about_dark.png?raw=true)|![](https://github.com/aFaneca/Marvel-Challenge/blob/main/screenshots/details_light.png?raw=true)|

|   Character list (landscape)   |   Character list (landscape & dark mode)   |
|:-------------:|:-------------:|
| ![](https://github.com/aFaneca/Marvel-Challenge/blob/main/screenshots/list_light_landscape.png?raw=true) |  ![](https://github.com/aFaneca/Marvel-Challenge/blob/main/screenshots/list_dark_landscape.png?raw=true) |



# Tech Overview
## Features implemented
- Characters list
    - Automatic pagination (infinite scrolling)
    - Search feature
- Character details
    - Overview and related content (comics, events, stories & series)
- Offline support
- Custom error handling (see [What Could Be Improved](#what-could-be-improved))
- Unit test coverage

## Building the project
1. Clone the repository
2. Rename the file `secret.properties.sample` to `secret.properties`
3. Insert your [API key](https://developer.marvel.com/docs) in the file
4. Build the project

```secret.properties
marvelApi.publicKey="<YOUR-API-KEY>"
```

## Arch
This app was developed based on **MVVM + Clean Architecture**, with an **offline-first** approach.

## Relevant dependencies
- [Dagger-Hilt](https://dagger.dev/hilt/) for dependency injection
- [Retrofit](https://square.github.io/retrofit/) for network requests
- [Glide](https://github.com/bumptech/glide) for image loading/caching
- [Room](https://developer.android.com/training/data-storage/room) for the local database
- Jetpack [Navigation ](https://developer.android.com/guide/navigation) for navigation
- [Material Components](https://material.io/develop/android/) for UI
- [Coroutines](https://developer.android.com/kotlin/coroutines) for background work

## UI
I've been tinkering with Jetpack Compose for the past year now and I believe it'll be the future of native android development.
I'm even currently [developing another project with Jetpack Compose](https://github.com/aFaneca/AFA-Score-Android) and it looks stabler than it ever did.
However, for this specific project, I chose to go with XML views (+ ViewBinding), since they are still very popular industry-wide.

## Offline-first & pagination
For these requirements, **Paging 3** immediately came to mind as a possible solution.
I could, in theory, delegate all the pagination and offline support functionality to that library and let it handle the implementation details behind the scenes.
However, I tend to not be a big fan of it, for a few reasons:
- A lot of boilerplate
- Very opinionated
- Difficult to integrate in a clean architecture without risking the purity of its layers (data, domain & ui)

For those reasons, I decided to implement my own custom solutions for both of these requirements.

### Offline support
I based my implementation on the **single source of truth** concept.
Every time I fetch remote data, I persist it locally to serve as a simple cache. All the data that flows from the data layer into the domain layer comes **exclusively** from the local data source.

1. User interaction triggers a request for new data
2. The app will try to fetch it directly from the web api
    * If Failure: jump to step 3
    * If Success: persist the data (not the whole payload, just essential data) locally in my local data source

3. Retrieve the requested data from the local data store, transform it and present it to the user

### Pagination
For pagination, I implemented a simple scroll listener with some logic to trigger a new page request when the user has scrolled to the bottom of the list.

For a smoother scrolling/lazy loading experience in a customer-facing app, this mechanism should be improved, by adding a custom threshold, so that we can request the next page a few moments before the user actually reaches the end of the list.

## Unit tests
I added unit tests to cover the most critical operations/state changes from these classes:
- AboutViewModel
- CharactersViewModel
- DetailsViewModel
- CharactersRepository
- TopSellingRepository


# What Could Be Improved
## Contextualised error handling
If we had control over the API, we would be able to work on normalizing the API responses with known error codes, so that we could map them into contextualized error prompts/messages that actually make sense to the end-user in the frontend. We should also map common client-side http exceptions into human readable error messages.

Currently, I simply show the localized message that accompanies the exception thrown in case of network/server errors. That means that the API endpoint (and other technical details) can be exposed to the user interface, which should never happen on a production app.
