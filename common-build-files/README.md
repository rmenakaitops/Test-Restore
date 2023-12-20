Common build files
==

This project contains gradle scripts that can be used by any WSO2 project, and useful environment files such as docker aliases. They provide the following:

* local deploy to components
* remote deploy to components
* sub component build and install
* handle pipeline promotion in Artifactory
* bash_aliases with docker shortcuts

Note that a number of these tasks rely on the necessary pom.xml files being present.

To run locally, the commit reference is added to the specific project.  Note that in the pipeline, Jenkins always checks out the master branch.

Reliable Delivery Creation
=

