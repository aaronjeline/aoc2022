#!/bin/bash

set -e

WIN=6
LOSS=0
TIE=3

rock=1
paper=2
scissor=3

function shape_score() { 
	if [ $2 == "scissor" ]; then
		# rock
		echo 1
	elif [ $2 == "paper" ]; then
		echo 2
	else 
		echo 3
	fi
}

function round_score() { 
	echo `win_score $1 $2` `shape_score $1 $2` + p | dc
}

function lose() { 
	if [ $1 == "A" ]; then 
		# rock
		echo $scissor
	elif [ $1 == "B" ]; then 
		# paper
		echo $rock
	else
		# scissor
		echo $paper
	fi
}

function win() { 
	if [ $1 == "A" ]; then 
		# rock
		echo $paper
	elif [ $1 == "B" ]; then 
		# paper
		echo $scissor
	else
		# scissor
		echo $rock
	fi
}

function tie() { 
	if [ $1 == "A" ]; then 
		# rock
		echo $rock
	elif [ $1 == "B" ]; then 
		# paper
		echo $paper
	else
		# scissor
		echo $scissor
	fi
}

function decide_outcome() { 
	local result
	if [ $2 == "X" ]; then
		result=$(lose $1)
	elif [ $2 == "Y" ]; then
		let "result= 3 + `tie $1`"
	else
		let "result= 6 + `win $1`"
	fi
	echo $result
}

total_score=0

while read p; do
	let "total_score = $total_score + `decide_outcome $p`"
done 

echo $total_score



