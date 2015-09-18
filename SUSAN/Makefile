CC = gcc
#CPP = g++
CMP = diff -s
RM  = rm -rf

GOLDFILE = golden.pgm
INFILE = input_small.pgm    
OUTFILE = output_edge.pgm

#------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
# ---- for compiling
#------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
# ---- the original make command
susan: susan.c
	$(CC) -static -O4 -o susan susan.c -lm  -g 

# ---- compiling the intact original file
susan_original: susan_original.c Makefile
	$(CC) -static -O4 -o susan_original  susan_original.c -lm  -g 

# ---- right now it is not working
susan_original_cpp: susan_original.c Makefile
	$(CPP) -o susan_original  susan_original.c -lm  -g 

# ---- allows for debugging 
susan_debug: susan.c Makefile
	$(CC) -static -O4 -o susan susan.c -lm  -g  -v -da -Q

debug_helpers:
	$(CC) debug_helpers.c debug_helpers_test.c -o debug_helpers -g

#------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
# ---- for running
#------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
# ---- for debugging in gdb
run_debug:
	gdb --args ./susan $(INFILE) $(OUTFILE) -e 

# ---- running the code with edth dietction simply
run:
	./susan $(INFILE) $(OUTFILE) -e
	$(CMP) $(OUTFILE) $(GOLDFILE)

# ---- copy of the run:
test:
	./susan $(INFILE) $(OUTFILE) -e 
	$(CMP) $(OUTFILE) $(GOLDFILE)

# ---- running the orignal one
run_original:
	./susan_original  $(INFILE) $(OUTFILE) -e 
	$(CMP) $(OUTFILE) $(GOLDFILE)


#debug_helpers:


#------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
# ---- house keeping
#------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
clean:
	$(RM) susan $(OUTFILE)
