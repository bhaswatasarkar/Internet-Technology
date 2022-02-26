const socket = io('http://localhost:8000');

const {username, roomname} = Qs.parse(location.search,{//username and the room for multicast
    ignoreQueryPrefix: true
});


const messageInput = document.getElementById('messageInp')//message send

//broadcast variables
const form = document.getElementById('send-container')
const broadcastbutton = document.querySelector('#broadcastbutton')
const broadcastoutput = document.querySelector('#broadcastcontainer')

//multicast variables
const formmulticast = document.getElementById('sender-form-multicast')
const multicastbutton = document.querySelector('#multicastbutton')
const multicastoutput = document.querySelector('#multicastcontainer')

const appendelement = (message,messageclassdesign,castingtype)=>{
    const newelement = document.createElement('div')
    newelement.innerText = message
    newelement.classList.add(messageclassdesign)
    if(castingtype=='broadcast')
        broadcastoutput.append(newelement)
    else if(castingtype=='multicast'){
        multicastoutput.append(newelement)
    }
    else if(castingtype=='unicast'){
        unicastoutput.append(newelement)
    }
    
}

// const username = prompt("Enter your name to join")
socket.emit('new-user-joined',({username,roomname}))

socket.on('user-joined',username=>{
    appendelement(`${username} joined the chat`,'left','broadcast')
})

form.addEventListener('submit',(e)=>{
    e.preventDefault();
    const message = messageInput.value
    appendelement(`You: ${message}`,'right','broadcast')
    socket.emit('sendbroadcast',message)
    messageInput.value=''
})

formmulticast.addEventListener('submit',(e)=>{
    e.preventDefault();
    const message = messageInput.value
    appendelement(`You: ${message}`,'right','multicast')
    socket.emit('sendmulticast',message)
    messageInput.value=''
})



socket.on('receive',data=>{
    appendelement(`${data.user.username}: ${data.message}`,'left','broadcast')
})

socket.on('receivemulticast',data=>{
    appendelement(`${data.user.username}: ${data.message}`,'left','multicast')
})
