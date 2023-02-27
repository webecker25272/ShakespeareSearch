# TODO 

### Algos
* need to make an Algo class that has run() behavior and algoName property
* should also figure out what the return of run() is-- the actual matches need to get rendered in a table in the front end.
* Algo class gets passed into the Iterator object
* what is the return of Iterator.run()? It should return the matches, but it should also pass info to the evalulator class, such as how many threads etc
* spark algo
* flink algo
* pure java fuzzy match
* pure java boyer moore?
* implement some kind of encoding search?


### APIs
* build API to serve shakespeare text to the search algos
* should have pagination, rate limiting etc
* build API to serve search results to the front end


### Front End
* render output from Algo.run()
* render output from Evaluator.evaluate()
* this should be a line chart or something that shows space/time complexity for each thread