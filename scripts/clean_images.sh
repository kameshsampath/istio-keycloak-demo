#!/usr/bin/env bash
# Remove all images that have master tag in it
docker images | grep key | awk '{n=$1":"$2; print "\x27" n "\x27"}' | xargs docker rmi 