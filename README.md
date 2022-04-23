# NYT-News
News application using [NYT APIs](https://developer.nytimes.com/)

This application is developed as part of a learning curve for Jetpack Compose.

Technologies and architectures followed:
- Written in [Kotlin](https://kotlinlang.org/)
- UI development in declarative style using [Jetpack Compose](https://developer.android.com/jetpack/compose) (Refer [Declarative UI development](https://increment.com/mobile/the-shift-to-declarative-ui/))
- Application architecture followed is Clean Architecture based on the MVVM model. This model of architecture consists of three main layers, namely data, domain and presenter.
- Dependency Injection using Hilt for Android
- Pagination implemented with [Paging3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) with Room DB acting as the mediator. (Refer [Page from network and database](https://developer.android.com/topic/libraries/architecture/paging/v3-network-db))
