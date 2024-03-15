## Link to forked repo: https://github.com/tomgrbz/cs4520-assg4-starter-code

## Project Overview

This project involves implementation for an android app featuring a login screen and a
products listing page. Rules for how to interact the rule have been made within the `nav_graph.xml`
file. There is only one action made which is the direction following a successful login with
username: `admin` and password: `admin`. If the credentials do not match, the user is prompted with
a toast message stating such.

Following successful login, the app will make an API call to fetch at a fixed endpoint a list of
products.
These products can be duplicated or incomplete, to which the ProductRepository will handle in
creating a set to check for duplicates,
and a Product companion function that checks for faulty fields for a given product. These sanitized
products are then inserted into a Room databse locally. The user can
then see a list of products, with each featuring an image,
name, expiry date (if applicable), and price. Food products have a yellow background while tools
have a red background, to distinguish between the two.

If the API call were to fail and there exists entries in the room database, then those are instead
displayed to the user.
If the API were to fail with NO records found in the DB then a message saying "No products available
will be displayed to the user".

Whilst the API records are loading, there is a spinning progress bar that spins. While testing
locally
it seems that this never appears to last too long, even when throttling connection.

To get back to the login page, the user can swipe to the right, from the left side of the screen.
Performing this action again, the user will then close the app.

To run this app in android studio, it has been tested on Pixel 3A on API level 34, and the generic
medium sized Android at the same level.

## Architecture

Much of the architectural discussion pertains to the ProductList fragment and its ViewModel. The
MVVM
pattern is used in separating out concerns. With the Product list using the recycler view to manage
individual product fragments, data is fetched from the view model and observed within the Fragment
class.
The Product model has simple implementation for its union type of either Food or Equipment, and
necessary methods
to check for correct instantiation and creation of a Product.

There exists a Product Mapper class to help map ProductResponse objects from the API to the Product
Model used for display, and
also from the Product Entity class for the Room DB to the Product Model.

A custom Application instance is used to instantiate an instance of a ProductRepository, so that the
ViewModel may have this dependency be
injected from the product list View. The ProductRepository itself has dependencies for managing the
local database source and its Retrofit
remote source for fetching from the API.

There is a single main activity which sets the navigation
graph for child fragments. The navigation graph has most of the movement logic in regards to
navigating the app, namely that the user is prompted with the login fragment which just holds logic
for checking the admin credentials are correct, to which it then uses the nav graph to emit the
action to move to the Product List page. At this page the user interacts with a list of products
using the Recycler view pattern, with an adapter made for creating the views of each product. 

 
