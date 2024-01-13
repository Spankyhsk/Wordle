FROM hseeberger/scala-sbt
WORKDIR /Wordle
ADD ./Wordle
CMD sbt test