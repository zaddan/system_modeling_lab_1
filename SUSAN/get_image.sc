#include "config.h"

/* char filename[200], unsigned char *in_global, int *x_size, int *y_size) */

behavior get_image(i_receiver Port)
{

FILE  *fd;
char header [100];
int  tmp;
/*
 unsigned char **in = &in_global;
 */ 
// the only instance of in was at line 40. Didn't need to be a pointer to a pointer. => changed to just a pointer


unsinged char *in; // added
Port.receive(fd, sizeof(FILE)); // added
Port.receive(in, sizeof(char *)); // added


#ifdef FOPENB
  if ((fd=fopen(filename,"rb")) == NULL)
#else
  if ((fd=fopen(filename,"r")) == NULL)
#endif
    exit_error("Can't input image %s.\n",filename);

  /* {{{ read header */

  header[0]=fgetc(fd);
  header[1]=fgetc(fd);
  if(!(header[0]=='P' && header[1]=='5'))
    exit_error("Image %s does not have binary PGM header.\n",filename);

  *x_size = getint(fd);
  *y_size = getint(fd);
  tmp = getint(fd);

/* }}} */

  //*in = (uchar *) malloc(*x_size * *y_size);

  if (fread(*in,1,*x_size * *y_size,fd) == 0)
    exit_error("Image %s is wrong size.\n",filename);

  fclose(fd);
}



