#provenance - a simple Java API for LIMS and analysis metadata

##Overview

The API model consists of a minimal set of LIMS metadata objects:

* [sample provenance](provenance-api/src/main/java/ca/on/oicr/gsi/provenance/model/SampleProvenance.java)
* [lane provenance](provenance-api/src/main/java/ca/on/oicr/gsi/provenance/model/LaneProvenance.java)

A single object to describe analysis metadata:
* [analysis provenance](provenance-api/src/main/java/ca/on/oicr/gsi/provenance/model/AnalysisProvenance.java)

And a linking object that can be used to link analysis metadata to its LIMS metadata:
* [lims key](provenance-api/src/main/java/ca/on/oicr/gsi/provenance/model/LimsKey.java)

Finally, using the above API model, LIMS and analysis metadata can be combined into a composite object that describes everything about a file:
* [file provenance](provenance-api/src/main/java/ca/on/oicr/gsi/provenance/model/FileProvenance.java)

##Building

```
mvn clean install
```

##Support

For support, please file an issue on the Github project or send an email to gsi@oicr.on.ca .
