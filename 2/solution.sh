#!/bin/bash

set -e

WIN=6
LOSS=0
TIE=3

function rock() { 
	if [ $1 == "X" ]; then 
		echo $TIE
	elif [ $1 == "Y" ]; then
		# paper
		echo $WIN
	else
		# scissor
		echo $LOSS
	fi
}

function paper() { 
	if [ $1 == "X" ]; then 
		# rock
		echo $LOSS
	elif [ $1 == "Y" ]; then
		# paper
		echo $TIE
	else
		# scissor
		echo $WIN
	fi
}

function scissor() { 
	if [ $1 == "X" ]; then 
		# rock
		echo $WIN
	elif [ $1 == "Y" ]; then
		# paper
		echo $LOSS
	else
		# scissor
		echo $TIE
	fi
}


function round_score() { 
	if [ $1 == "A" ]; then
		rock $2
	elif [ $1 == "B" ]; then 
		paper $2
	else 
		scissor $2
	fi
}

function shape_score() { 
	if [ $2 == "X" ]; then
		# rock
		echo 1
	elif [ $2 == "Y" ]; then
		echo 2
	else 
		echo 3
	fi
}


total_score=0

while read p; do
	round_score=$(echo `round_score $p` `shape_score $p` + p | dc)
	total_score=$(echo $round_score $total_score + p | dc)
done 

echo $total_score



