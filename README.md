# StoryTelPosts
StoryTelTask for android candidate

This project is completely rewritten in Kotlin and follows the below architecture guidelines

1.Usage of Mvvm pattern
2.Retrofit with coroutines for making network calls
3.mosi KotlinJsonAdapterFactory from square is added as a converter factory which is recommended by google 
4.Timber for better logging
5.Picaso for easy image loading
6.material design cards
7.LisAdapter pattern for best usage of recyclerviews
8.All screens use data binding to bind the data and handle click events efficiently avoiding all findviewbyid's and setters to set data to views
9.seperation of packages for ui, util, network and entity layers
10.Efficient network error handling using LiveData
11.Usage of BindingAdapters and kotlin extension functions in a utility file


