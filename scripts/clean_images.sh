#!/usr/bin/env bash
# Remove all images that have master tag in it
docker images | grep master | awk '{n=$1":"$2; print n}' | xargs docker rmi -