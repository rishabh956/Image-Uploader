import React, { Component } from 'react';

class ImageUploader extends Component {


     handleFileChange=(event)=>{

     }

      formSubmit=(event)=>{

     }

    render() {
        return (
            <div className="main flex justify-center">

                <div className="rounded card w-1/3 border shadow m-4 p-4 bg-gray-200">
                    <h1 className="text-2xl">Image Uploader</h1>

                    <div className='form_container mt-3'>
                        <form action="">
                        <div className='field_container flex flex-col gap-y-2'>
                            <label htmlFor=''>Select the Image</label>
                            <input  type='file' id='file'></input>
                        </div>
                        <div className='field_container text-center mt-3'>
                        <button className='rounded px-3 py-1 bg-blue-700 hover:bg-blue-500 text-white'>Upload</button>
                        <button  className='rounded ml-2 px-3 py-1 bg-orange-600 hover:bg-orange-400 text-white'>Clear</button>
                        </div>
                        </form>

                    </div>

                </div>
                
            </div>
        );
    }
}



export default ImageUploader;