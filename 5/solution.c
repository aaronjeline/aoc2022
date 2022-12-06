#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct dock {
	char stacks[20][100];
	int ptrs[20];
};

void new_dock(struct dock* d) {
	int i;
	for(i = 0; i < 20; i++) 
		d->ptrs[i] = 0;
}

void push(struct dock*, int, char);
char pop(struct dock*, int);

void parse_setup(struct dock*);

void parse_setup_line(struct dock*, char*);

void execute_instrs(struct dock*);
void execute_instr(struct dock*, char*);
void show_answer(struct dock*);

void dbg_stacks(struct dock*);

#define STACKS 9

int main(void) {
	struct dock d;
	new_dock(&d);

	parse_setup(&d);
	dbg_stacks(&d);

	execute_instrs(&d);

	show_answer(&d);

	return 0;
}

void dbg_stacks(struct dock *d) { 
	return;
	int i;
	for (i = 1; i <= STACKS; i++) {
		char *s;
		if (d->ptrs[i]) { 
			s = calloc(sizeof(char), d->ptrs[i] + 1);
			memcpy(s, d->stacks[i], d->ptrs[i]);
			printf("Stack %d is %s\n", i, s);
			free(s);
		} else 
			printf("Stack %d is empty\n", i);
	}
}


void show_answer(struct dock *d) { 
	int i;
	char answer[STACKS + 1] = { '\0' };

	for (i = 1; i <= STACKS; i++) { 
		answer[i-1] = pop(d, i);
	}

	puts(answer);
}

void execute_instrs(struct dock *d) { 
	char line[100];

	while(1) { 
		fgets(line, 100, stdin);
		if (feof(stdin))
			break;
		execute_instr(d, line);
	}

}


void execute_instr(struct dock *d, char *line) { 
	int amnt, src, dst, i;
	char tmp;

	sscanf(line, "move %d from %d to %d", &amnt, &src, &dst);

	for (i = 0; i < amnt; i++) {
		tmp = pop(d, src);
		push(d, dst, tmp);
	}

}

void parse_setup(struct dock *d) {
	char lines[10][100];
	int count = 0;

	while (1) {
		fgets(lines[count], 99, stdin);
		if (*lines[count] == '\n')
			break;
		count++;
	}

	for (count -= 2; count >= 0; count--)
		parse_setup_line(d, lines[count]);

}

void parse_setup_line(struct dock *d, char *line) {
	int offset = 1, idx = 1, len, pos;

	len = strlen(line);

	for (; offset < len; offset += 4)  {
		if (line[offset] != ' ')
			push(d, idx, line[offset]);
		idx++;
	}
			
	dbg_stacks(d);
}


void push(struct dock* d, int idx, char c) { 
	d->stacks[idx][d->ptrs[idx]] = c;
	d->ptrs[idx]++;
}


char pop(struct dock *d, int idx) { 
	int offset = d->ptrs[idx] - 1;
	d->ptrs[idx] -= 1;
	return d->stacks[idx][offset];
}

