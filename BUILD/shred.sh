#!/bin/sh

CWD="$( cd "$( dirname "$0" )" && pwd )"
if [ ! -f "$CWD/SHELLSHOCK" ]; then exit; fi

HOME="$( cd "$CWD/.." && pwd )"
if [ ! -f "$HOME/BUILD/SHELLSHOCK" ]; then exit; fi

cd $HOME && git ls-files -c --ignored --exclude-standard | sed 's/.*/\"&\"/' | xargs git rm -r --cached)