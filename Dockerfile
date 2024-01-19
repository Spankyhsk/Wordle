FROM hseeberger/scala-sbt:8u222_1.3.5_2.13.1
WORKDIR /wordle
ADD . /wordle
CMD sbt test && sbt run