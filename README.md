Tagifai
====================
A command line tool to process images (JPGs and PNGs) and assign tags (searchable keywords) to them using Clarifai API.
Tagged images can then be searched using your operating system search application (Mac: cmd + spacebar, Windows: ctrl + F3)


![Example Usage](Example/how.to.use.png?raw=true "Example usage: searching for pictures with group tag")


Uage
---------------
```
mvn clean package

export CLARIFAI_APP_ID=your_client_id
export CLARIFAI_APP_SECRET=your_client_secret

java -jar target/Tagifai-1-jar-with-dependencies.jar ~/absolute/path/to/directory/that/contains/images-to-tag/ [-D]

* Optional -D tag to specify whether or not to delete the original files after tagging them. 
```
Results will be under folder parentFolder/Output/


Setup
---------------

* Sign up for a free Clarifai account at: https://developer.clarifai.com/signup/champs
* Create new application at [Clarifai's applications dashboard](https://developer.clarifai.com/applications/).
* Grab the Client_Id (aka APP_ID) and Client Secret (aka APP_SECRET)

To avoid having to provide the API keys everytime you wanted to run the app
Add the api keys to your enviornment variable. (.bashrc, .zshrc, .fishrc or if you are  in windows just search enviornment variabls)
Alternately modifay ClarifaiClient constructor in the Tagifai.java
'''	ClarifaiClient	clarifai = new ClarifaiClient(APP_ID, APP_SECRET); '''

Requirements
------------
* JDK 8 or later
* Maven 
* Clarifai's free account

WIP
------------
Add tags so that dropbox/google photos able to search the files
Add an easy to use interface
