# java-fast-scanner
`FastScanner` Class aims to provide simple methods for scanning inputs faster than `Scanner` and `BufferedReader`. 

The Scanner Class in Java is very slow which can reduce the performance of your code. FastScanner uses Buffer for the input stream to speed up input while providing methods for 
reading java primitive data types.

## Performance
> `Scanner` typically has very high execution time and hence squeezes the `BufferedReader` and `FastScanner` graph to bottom. So for better visibility, Scanner data is excluded from the graph. 

#### &emsp; String Read Performance &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; Long Read Performance

<img align="left" width="403" height="302" src="https://github.com/KaustubhDandekar/java-fast-scanner/blob/master/benchmark/images/string_read_benchmark.PNG" alt="String Read Benchmark" title="Optional title">
 
<img align="right" width="403" height="302" src="https://github.com/KaustubhDandekar/java-fast-scanner/blob/master/benchmark/images/long_benchmark.PNG" alt="Long Benchmark">

<br><br><br><br><br><br><br><br><br><br><br><br><br>
#### &emsp; Double Read Performance
<img width="403" height="302" style="border:1px solid grey" src="https://github.com/KaustubhDandekar/java-fast-scanner/blob/master/benchmark/images/double_benchmark.PNG" alt="Double Benchmark">

## Usage

#### Creating `FastScanner` Object
> `FastScanner` object can be created using different data streams similar to `Scanner` Class
```java
  FastScanner fastScanner = new FastScanner(new InputStream|FileInputStream|File|Path|String);
```

#### Reading `Integer` or `Long` Values
> `FastScanner` provides two types of methods: Fast and Strict. 
> Fast methods assumes that the input always will be Integer or Long thus do not validate for any invalid characters.
 Whereas Strict method will throw `InputMismatchException` for any invalid characters with some loss in performance.
 ```java
  int a = fastScanner.nextIntFast();
  int b = fastScanner.nextIntStrict();
  long c = fastScanner.nextLongFast();
  long d = fastScanner.nextLongStrict();
```

#### Reading `Float` or `Double` Values
> 
 ```java
  float a = fastScanner.nextFloatFast();
  float b = fastScanner.nextFloatStrict();
  double c = fastScanner.nextDoubleFast();
  double d = fastScanner.nextDoubleStrict();
```

#### Reading `Number` inside the `InputStream`
> `FastScanner` can be used for reading the numbers in a Text for some specific use cases such as reading numbers from a stream of stringified array or a configuration file.
 ```java
  ArrayList<Double> array;
  while(fastScanner.hasNext()){
    array.add(fastScanner.nextNumberInText());
  }
```

