# movie-assignment
 Movie-Assignment for Omdb API with clean, testable and maintainable Android application with Multi Module Architecture 

 ## Libraries, Programming Language and tools
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines/coroutines-guide.html)
- [Koin](https://insert-koin.io/)
- [Retrofit](https://square.github.io/retrofit/)
- [Room](https://developer.android.com/training/data-storage/room/index.html)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Architecture components](https://developer.android.com/topic/libraries/architecture/)
- [JUnit4](https://junit.org/junit4/)
- [Mockk](https://mockk.io/)

## Architecture
SOLID principles - Separate an application into units, with minimal overlapping between the functions of the individual units
The project architecture follows the principles of Clean Architecture. 

### Modularisation
Each layers implemented as a its own module in order to keep them separate in alignment with clean architecture and enable parallel builds in gradle.  

### View
Android UI components belong here. View communicates with ViewModel, subscribing to ViewState 
using StateFlow. Each View has a ViewModel attached that provides the data required by 
the view to make state transitions.

### Presentation
This module provides the logic of the View, by providing subscribing components to the view to 
react to. The ViewModel changes the ViewState based on the use case responses.

### Domain
Contain use cases of the application. They provide the application specific business rules and are 
are responsible of accessing data from different repositories, combine and transform them, to 
provide single use case business rule.

### Data
The Data layer is our access point to external data layers and is used to fetch data from multiple 
sources (network, cache). It contains implementations of Repositories, which request data from 
necessary RemoteDataSources and CacheDataSources to feed the use case and make communication 
between the 2 types of data sources.

### Remote
The Remote layer handles all communications with remote sources such as API calls using a Retrofit 
interface. A RemoteImpl class implements a Remote interface from the Data layer and uses a Service 
to retrieve data from the API.

### Cache
The Cache layer handles all communication with the local databasea.

### Unit tests for OMDB API  
Run the following command in a terminal window at the root level of the project
`./gradlew test`
