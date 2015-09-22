#include "config.h"
//void get_image(filename,in_global)
//  char           filename[200];
//  unsigned char  *in_global;
//{
behavior get_image(i_receiver inPort, i_sender outPort) {
    void main(void) { 
    char           filename[FILE_NAME_SIZE];
    inPort.receive(filename, FILE_NAME_SIZE);
    unsigned char  in_global[IMAGE_SIZE_CONST]; 
//     inPort.receive(in_global, IMAGE_SIZE_CONST);
     
    FILE  *fd;
    char header [100];
    int  tmp;
    
    unsigned char **in = &in_global;
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

    int x_size =  X_SIZE_CONST;
    int y_size =  Y_SIZE_CONST;

    int dummy = getint(fd); //necessary cause it calls fgetc
    dummy = getint(fd);
    tmp = getint(fd);

    /* }}} */

    //*in = (uchar *) malloc(*x_size * *y_size);

    if (fread(*in,1,x_size * y_size,fd) == 0)
        exit_error("Image %s is wrong size.\n",filename);

    fclose(fd);


    outPort.send(in, IMAGE_SIZE_CONST);
}
};
