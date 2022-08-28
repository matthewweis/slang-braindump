#!/bin/sh

# PWD="$( pwd )" # already defined...
CWD="$( cd "$( dirname "$0" )" && pwd )"

echo "CWD=$CWD and PWD=$PWD"

echo "going up a directory..."
cd ..
echo "CWD=$CWD and PWD=$PWD"
