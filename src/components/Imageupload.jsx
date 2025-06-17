import React, { useEffect, useRef, useState } from 'react';
import axios from 'axios' ;
function Imageupload(props) {
    
    const [selectedFile,setSelectedFile]= useState(null);
    const [files,setFiles] = useState([]);
    const [messageStatus,setMessageStatus] = useState(false);
    const [uploading,setUploading] = useState(false);
    const [uploadedSrc,setUploadedSrc] = useState("");
    const ref = useRef() ;

    useEffect(() => {
        getFiles();
    },[]);

    const getFiles = () => {

        const url = `http://localhost:8080/api/v1/s3`;
        axios.get(url).
        then((response) => {
            setFiles(response.data);
        }).
        catch((error) => {
            console.log(error);
        });
    };

    const handleFileChange=(event)=>{
        const file = event.target.files[0];
        if(file.type==='image/png'  || file.type==='image/jpeg')
        {
            setSelectedFile(file);
        }
        else
       {
          alert("Select the Image file only");
          setSelectedFile(null);
       }
     }

      const formSubmit=(event)=>{
        
        event.preventDefault();
        console.log(selectedFile);
        if(selectedFile)
        {
            uploadImageToServer();
        }
        else
        {
            alert('Select Image First !!');
        }

     }

     const uploadImageToServer=(event)=>{

        const url = `http://localhost:8080/api/v1/s3`;
        const data = new FormData() ;
        data.append('file',selectedFile);
        setUploading(true);
        axios.post(url,data).
        then((response) => {
            console.log(response.data);
            setUploadedSrc(response.data);
            setMessageStatus(true);
            getFiles();
        }).
        catch((error) =>{
            console.log(error);
        }
            
        ).
        finally(()=>{
            console.log('request finished');
            setUploading(false);
        });
     }

     const resetHandler = () =>{
        ref.current.value="";
        setSelectedFile(null);
        setMessageStatus(false);
     }


    return (
            <div className="main flex flex-col items-center justify-center">

                <div className="rounded card w-1/3 border shadow m-4 p-4 bg-gray-200">
                    <h1 className="text-2xl">Image Uploader</h1>

                    <div className='form_container mt-3'>
                        <form action="" onSubmit={formSubmit}>
                        <div className='field_container flex flex-col gap-y-2'>
                            <label htmlFor=''>Select the Image</label>
                            <input onChange={handleFileChange} ref={ref} type='file' id='file'></input>
                        </div>
                        <div className='field_container text-center mt-3'>
                        <button  type='submit' className='rounded px-3 py-1 bg-blue-700 hover:bg-blue-500 text-white'>Upload</button>
                        <button type='button' onClick={resetHandler} className='rounded ml-2 px-3 py-1 bg-orange-600 hover:bg-orange-400 text-white'>Clear</button>
                        </div>
                        </form>

                    </div>

                    {/* alert box */}

                    {messageStatus && <>
                    
                    <div class="mt-2 bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded relative" role="alert">
                    <strong class="font-bold">Success! </strong>
                    <span class="block sm:inline">Your Image is  uploaded successfully.</span>
                    </div>

                    </>}

                    {/* Uploading text view */}

                    {uploading && <div className='p-3 text-center'>
                        <div class="flex items-center justify-center my-3">
                            <div class="w-12 h-12 border-4 border-blue-500 border-dashed rounded-full animate-spin"></div>
                            <h1 className='mt-4'>Uploading...</h1>
                        </div>
                        </div>}

                    {/* Upload Image View */}
                    {messageStatus && <div className='uploaded'>
                        <img className='h-[300px] w-[200px] mx-auto mt-4 rounded shadow' src={uploadedSrc} alt=''/>
                    </div>}
                </div>

                {/* Uploaded image view */}
                <div className='mt-4 px-4 justify-center flex flex-wrap'>
                        {files.map(img => (
                    <img className='h-[200px] w-[150px] m-2 shadow rounded ' src={img} key={img} />
                    ))}
                </div>
                

            </div>
        );
}

export default Imageupload;