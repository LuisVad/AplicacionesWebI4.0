import React from 'react'
import styled, {keyframes} from "styled-components"

const rotate360 = keyframes`
    from{
        transform: rotate(0deg);
    } to{
        transform: rotate(360deg);
    }
`;

const Spinner = styled.div`
    margin: 16px;
    animation: ${rotate360} 1s linear infinite;
    transform: translateZ(0);
    border-top: 2px solid grey;
    border-rigth: 2px solid grey;
    border-bottom: 2px solid grey;
    border-left: 4px solid black;
    background: transparent;
    width: 80px;
    height: 80px;
    border-radius: 50%;
`;
const Loading = () => {
  return (
    <div style={{padding: '24px'}}>
        <Spinner/>
    </div>
  )
}

export default Loading
