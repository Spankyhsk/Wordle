FROM hseeberger/scala-sbt
WORKDIR /wordle
ADD . /wordle
CMD sbt test