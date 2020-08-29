# Kotlin Flow API

## Flow API
in Kotlin is a better way to handle the stream of data asynchronously that executes sequentially.
In RxJava, Observables type is an example of a structure that represents a stream of items. Its body does not get executed until it is subscribed to by a subscriber. and once it is subscribed, subscribers start getting the data items emitted. 
Similarly, Flow works on the same condition where the code inside a flow builder does not run until the flow is collected.

## Builders in Flow API
### flowOf()
It is used to create flow from a given set of values.
```
flowOf(1,3,2,4,6,5).onEach {
      Log.d("TAG", "Emitting $it")
      delay(400)
}.flowOn(Dispatchers.Default)
```
### asFlow()
It is an extension function that helps to convert type into flows.
```
(1..5).asFlow().onEach{ delay(300)}.flowOn(Dispatchers.Default)
```

### flow {}
This is a builder function to construct arbitrary flows.
```
flow {
    Log.d(TAG, "Start flow")
    (0..10).forEach {
        // Emit items with 500 milliseconds delay
        delay(500)
        Log.d(TAG, "Emitting $it")
        emit(it)
    }
}.map {
    it * it
}.flowOn(Dispatchers.Default)
```

### channelFlow {}
This builder creates cold-flow with the elements using send provided by the builder itself. `Cold-flow` means the flow is cold / not active until the function to make it flow gets called (collect{}).
To use channelFlow, we have to declare `@ExperimentalCoroutinesApi` annotation on the function.
```
channelFlow {
    (0..10).forEach {
        send(it)
    }
}.flowOn(Dispatchers.Default)
```

## Terminal Operators
**Terminal operators** are the operators that actually start the flow.
### Collect
```
(1..5).asFlow()
.filter {
    it % 2 == 0
}
.map {
    it * it
}.collect {
    Log.d(TAG, it.toString())
}
```
### Reduce
```
val result = (1..5).asFlow()
    .reduce { a, b -> a + b }

// print 15
Log.d(TAG, result.toString())
```

## Zip Operator
**Zip Operator** is an operator that combines the emissions of two flow collections together via a specified function and emits single items for each combination based on the results of this function.
```
val flowInt = flowOf(1, 2, 3)
val flowString = flowOf("A", "B", "C")
flowInt.zip(flowString) { intValue, stringValue ->
    "$intValue$stringValue"
}.collect {
    Log.d(TAG, it)
}
```

## Retry Operator
### retryWhen
```
.retryWhen { cause, attempt ->
    if (cause is IOException && attempt < 3) {
        delay(2000)
        return@retryWhen true
    } else {
        return@retryWhen false
    }
}
```
### retry
```
.retry(retries = 3) { cause ->
    if (cause is IOException) {
        delay(2000)
        return@retry true
    } else {
        return@retry false
    }
}
```
