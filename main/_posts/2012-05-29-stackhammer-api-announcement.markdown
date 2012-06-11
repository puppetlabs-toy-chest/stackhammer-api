---
layout: post
title: stackhammer-api announced
---
We are happy to announce the availability of the stackhammer-api project which provides a RESTful API to
communicate with Cloudsmith's [Stack Hammer](https://www.cloudsmith.com) service. The project contains a Java implementation of the API as
well as detailed documentation.

The API currently supports validation and deployment of Puppet configurations (stacks) stored in a repository
at GitHub and is used by the [Stack Hammer Jenkins plugin](https://wiki.jenkins-ci.org/display/JENKINS/Stack+Hammer+Plugin) to support continuous delivery workflows.
