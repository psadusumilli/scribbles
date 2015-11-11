AKKA STREAMING
Akka-Streams provide a higher-level abstraction over Akka’s existing actor model.
Can we abstract the functionality we want to achieve with Actors into a set of function calls? Can we treat Actor Messages as Inputs and Outputs to Functions, with type safety? Hello, Akka-Streams.
'Intent': Fix all shortcomings in plain actor messagsing, add backpressure, buffering, transformations and failure recovery.
Akka streaming APIs are more geared towards with end-users, while Reactive Stream specifications are followed for internal communication

'----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------'
SAMPLE
http://bryangilbert.com/blog/2015/02/04/akka-reactive-streams/

'reactive stream apis'
      public interface Subscriber<T> {
          public void onSubscribe(Subscription s);
          public void onNext(T t);
          public void onError(Throwable t);
          public void onComplete();
      }
      public interface Subscription {
          public void request(long n); //backpressure mechanism
          public void cancel();
      }
      public interface Publisher<T> {
          public void subscribe(Subscriber<? super T> s);
      }

      'FibonacciPublisher=>DoublingProcessor=>FibonacciSubscriber'

      class FibonacciPublisher extends ActorPublisher[BigInteger] with ActorLogging {      // 1
              var prev = BigInteger.ZERO
              var curr = BigInteger.ZERO

              def receive = {
                      case Request(cnt) =>                                                             // 2
                        log.debug("[FibonacciPublisher] Received Request ({}) from Subscriber", cnt)
                        sendFibs()
                      case Cancel =>                                                                   // 3
                        log.info("[FibonacciPublisher] Cancel Message Received -- Stopping")
                        context.stop(self)
                      case _ =>
              }
              def sendFibs() {
                      while(isActive && totalDemand > 0) {                                             // 4
                        onNext(nextFib())
                      }
              }
              def nextFib(): BigInteger = {
                      if(curr == BigInteger.ZERO) {
                        curr = BigInteger.ONE
                      } else {
                        val tmp = prev.add(curr)
                        prev = curr
                        curr = tmp
                      }
                      curr
                    }
      }
      class FibonacciSubscriber(delay: Long) extends ActorSubscriber with ActorLogging {       // 1
           val requestStrategy = WatermarkRequestStrategy(50)                                     // 2
           def receive = {
             case OnNext(fib: BigInteger) =>                                                      // 3
               log.debug("[FibonacciSubscriber] Received Fibonacci Number: {}", fib)
               Thread.sleep(delay)
             case OnError(err: Exception) =>                                                      // 4
               log.error(err, "[FibonacciSubscriber] Receieved Exception in Fibonacci Stream")
               context.stop(self)
             case OnComplete =>                                                                   // 5
               log.info("[FibonacciSubscriber] Fibonacci Stream Completed!")
               context.stop(self)
             case _ =>
           }
        }
        class DoublingProcessor extends ActorSubscriber with ActorPublisher[BigInteger] {   // 1
              val dos = BigInteger.valueOf(2L)
              val doubledQueue = MQueue[BigInteger]()                                           // 2

              def receive = {
                case OnNext(biggie: BigInteger) =>                                              // 3
                  doubledQueue.enqueue(biggie.multiply(dos))
                  sendDoubled()
                case OnError(err: Exception) =>                                                 // 4
                  onError(err)
                  context.stop(self)
                case OnComplete =>                                                              // 5
                  onComplete()
                  context.stop(self)
                case Request(cnt) =>                                                            // 6
                  sendDoubled()
                case Cancel =>                                                                  // 7
                  cancel()
                  context.stop(self)
                case _ =>
              }
              def sendDoubled() {
                while(isActive && totalDemand > 0 && !doubledQueue.isEmpty) {                   // 8
                  onNext(doubledQueue.dequeue())
                }
              }
              val requestStrategy = new MaxInFlightRequestStrategy(50) {                        // 9
                def inFlightInternally(): Int = { doubledQueue.size }
              }
           }

'----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------'
'Source': something with exactly one output stream
'Sink': something with exactly one input stream
'Flow': something with exactly one input and one output stream
'BidirectionalFlow': something with exactly two input streams and two output streams that conceptually behave like two Flows of opposite direction
'Graph': a packaged stream processing topology that exposes a certain set of input and output ports, characterized by an object of type Shape.


onNext => handles good messages
onError => the stream is broken

SAMPLE
implicit val system = ActorSystem("reactive-tweets")
implicit val materializer = ActorMaterializer()

val authors: Source[Author, Unit] =
  tweets
    .filter(_.hashtags.contains(akka))
    .map(_.author)

authors.runWith(Sink.foreach(println))


'----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------'



'----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------'
