#!/usr/bin/env bash

echo "Visualizing..."
gource --seconds-per-day 1 --auto-skip-seconds .3 --key --title "IRCBot Development"
echo "Visualization complete!"
