package main
import (
	"fmt"
	"bufio"
	"os"
)

type tuple struct { 
	a int
	b int
}

func (lhs *tuple) contains(rhs *tuple) bool {
	if lhs.a <= rhs.a { 
		return rhs.a <= lhs.b
	} else {
		return false
	}
}


type rec struct { 
	first tuple
	second tuple
}

func (r *rec) overlaps() bool {
	return r.first.contains(&r.second) || r.second.contains(&r.first)
}

func parseRec(s string) *rec { 
	t1 := tuple{}
	t2 := tuple{}

	fmt.Sscanf(s, "%d-%d,%d-%d", &t1.a, &t1.b, &t2.a, &t2.b)

	r := rec{ first : t1, second : t2 }

	return &r
}

func main() { 

	readFile, _ := os.Open("./sample.in")

	fileScanner := bufio.NewScanner(readFile)

	fileScanner.Split(bufio.ScanLines)

	count := 0

	for fileScanner.Scan() { 
		line := fileScanner.Text()
		r := parseRec(line)
		if r.overlaps() {
			count = count + 1
		}
	}

	readFile.Close()

	fmt.Printf("count = %d\n", count)

}
