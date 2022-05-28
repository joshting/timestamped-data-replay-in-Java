# Example of data replay of timestamped data in Java

This is a simple implementation of playback of recorded real-time data that contains timestamp.  Because there is a timestamp on each of the data points, the events does not need to occur at a constant rate.
The _Replay_ class is an Runnable implementation that takes in a list of _TimedData_, the _tick_ and the callback _ReplayEventListener_.  _Replay_ gives methods to control the replay such as pause, forward, rewind, seek and repeat.
See the example code for how the package can be used to replay a timestamped data.