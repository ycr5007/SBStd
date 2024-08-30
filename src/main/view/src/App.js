import axios from "axios";
import React, {useEffect} from "react"
import './App.css';

function App() {
  useEffect(() => {
    axios.get('/sbStd')
        .then(response => console.log(response))
        .catch(error => console.log(error))
  }, [])

  return (
    <div>

    </div>
  );
}

export default App;
