#include "config.h"
//void put_image(filename,in)
//    char filename [100],
//    *in;
//{
behavior put_image(ireceiver inPort){
    char in[IMAGE_SIZE_CONST];
    char           filename[FILE_NAME_SIZE];
    inPort.receive(filename, FILE_NAME_SIZE);
    inPort.receive(in, IMAGE_SIZE_CONST);
    FILE  *fd;
    int x_size =  X_SIZE_CONST;
    int y_size =  Y_SIZE_CONST;



#ifdef FOPENB
    if ((fd=fopen(filename,"wb")) == NULL) 
#else
        if ((fd=fopen(filename,"w")) == NULL) 
#endif
            exit_error("Can't output image%s.\n",filename);

    fprintf(fd,"P5\n");
    fprintf(fd,"%d %d\n",x_size,y_size);
    fprintf(fd,"255\n");

    if (fwrite(in,x_size*y_size,1,fd) != 1)
        exit_error("Can't write image %s.\n",filename);

    fclose(fd);
}
