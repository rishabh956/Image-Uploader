import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import Imageupload from './components/Imageupload'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <Imageupload/>
    </>
  )
  
}

export default App
