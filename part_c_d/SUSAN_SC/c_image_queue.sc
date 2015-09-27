// c_image_queue.sc:	instantiation of template c_typed_queue.sh
//			with type 'image'

#include <c_typed_queue.sh>	/* make the template available */
#include "constant.hh"

// define the tranceiver interface for data type 'img'
DEFINE_I_TYPED_TRANCEIVER(image, img)

// define the sender interface for data type 'img'
DEFINE_I_TYPED_SENDER(image, img)

// define the receiver interface for data type 'img'
DEFINE_I_TYPED_RECEIVER(image, img)


// define the queue channel for data type 'img'
DEFINE_C_TYPED_QUEUE(image, img)


// EOF c_image_queue.sc
