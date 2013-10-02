JGeocoder
=========

JGeocoder is a free, open source geocoder implemented in Java. This particular 
flavor has been branched fron JGeocoder-NYSenate and contains only the parsing 
library. It's also been modified to support thread interruption, via the ParseAddress 
class. This was done to support parsing of random, large strings that may take 
too long to parse, and should thus be abandoned. If you have no need for this 
use-case, then you're likely better off using another fork of JGeocoder.

License
=========

http://www.apache.org/licenses/LICENSE-2.0.html

