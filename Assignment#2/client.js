// const e = require("express");

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

//unicast variables
const formunicast = document.getElementById('sender-form-unicast')
const unicastbutton = document.querySelector('#unicastbutton')
const unicastoutput = document.querySelector('#unicastcontainer')

const appendelement = (message,messageclassdesign,castingtype)=>{
    const newelement = document.createElement('div')
    newelement.innerText = message
    newelement.classList.add(messageclassdesign)
    if(castingtype=='broadcast')
        broadcastoutput.append(newelement)
    else if(castingtype=='multicast'){
        multicastoutput.append(newelement)
    }  
}


const appendunicastelement = (message,messageclassdesign,username)=>{
    //const unicastoutput = document.querySelector(`#`+`${username}`)
    const unicastnewuser = document.getElementById(`${username}`)
    const newelement = document.createElement('div')
    newelement.innerText = message
    newelement.classList.add(messageclassdesign)
    unicastnewuser.append(newelement)

    return unicastnewuser;
}



socket.emit('new-user-joined',({username,roomname}))

socket.on('user-joined',username=>{
    appendelement(`${username} joined the chat`,'middle','broadcast')
})

socket.on('user-joined-room',username=>{
    appendelement(`${username} joined the chat`,'middle','multicast')
})

socket.on('left',(left_username)=>{
    appendelement(`${left_username} left the chat`,'middle','broadcast')
    appendelement(`${left_username} left the chat`,'middle','multicast')
})



form.addEventListener('submit',(e)=>{
    e.preventDefault();
    const message = messageInput.value
    appendelement(`You: ${message}`,'right','broadcast')
    socket.emit('sendbroadcast',message)
    messageInput.value=''

    
    const files = document.getElementById('imageInp').files[0]
    if(files){
        const fileReader = new FileReader();
        const imgPreview = document.createElement('div');
        //imgPreview.classList.add('right')
        result = fileReader.readAsDataURL(files);
        fileReader.addEventListener("load", function () {
            imgPreview.style.display = "block";
            imgPreview.innerHTML = '<img src="' + this.result + '"class = "image-message" />';

            socket.emit('sendimagebroadcast', this.result);
          }); 
       broadcastoutput.append(imgPreview);
    }

    document.getElementById('imageInp').value=''
    
})


formmulticast.addEventListener('submit',(e)=>{
    e.preventDefault();
    const message = messageInput.value
    appendelement(`You: ${message}`,'right','multicast')
    socket.emit('sendmulticast',message)
    messageInput.value=''

    const files = document.getElementById('imageInp').files[0]
    if(files){
        const fileReader = new FileReader();
        const imgPreview = document.createElement('div');
        result = fileReader.readAsDataURL(files);
        fileReader.addEventListener("load", function () {
            imgPreview.style.display = "block";
            imgPreview.innerHTML = '<img src="' + this.result + '"class = "image-message" />';
            socket.emit('sendimagemulticast', this.result);
          }); 
      multicastoutput.append(imgPreview);
    }

    document.getElementById('imageInp').value=''
    
    
})


formunicast.addEventListener('submit',(e)=>{
    e.preventDefault();
    const nametosend = document.getElementById("nametosendunicast").value

    const message = messageInput.value
    const newelement = document.createElement('div')

    if(document.getElementById(`${nametosend}`)===null){
        newelement.innerHTML=`<p>
            <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#collapseWidthExample" aria-expanded="false" aria-controls="collapseWidthExample">
            ${nametosend}
            </button>
        </p>
        <div style="min-height: 120px;">
            <div class="collapse collapse-horizontal" id="collapseWidthExample">
            <div class="card card-body" style="width: 300px;" id="${nametosend}">
            </div>
            </div>
        </div>`
        unicastoutput.appendChild(newelement)
    }
    output_temp = appendunicastelement(`You: ${message}`,'right',nametosend)
    socket.emit('sendunicast',{message,nametosend})
    messageInput.value=''

    const files = document.getElementById('imageInp').files[0]
    if(files){
        const fileReader = new FileReader();
        const imgPreview = document.createElement('div');
        result = fileReader.readAsDataURL(files);
        
        fileReader.addEventListener("load", function () {
            imgPreview.style.display = "block";
            image = this.result
            imgPreview.innerHTML = '<img src="' + image + '"class = "image-message" />';
            socket.emit('sendimageunicast', {image,nametosend});
          }); 
          output_temp.append(imgPreview);
    }

    document.getElementById('imageInp').value=''
    

})




socket.on('receive',data=>{
    appendelement(`${data.user.username}: ${data.message}`,'left','broadcast')
})

socket.on('receivemulticast',data=>{
    appendelement(`${data.user.username}: ${data.message}`,'left','multicast')
})

socket.on('receiveunicast',data=>{
    namefromreceived = data.user.username
    const newelement = document.createElement('div')
    if(document.getElementById(`${namefromreceived}`)===null){
        newelement.innerHTML=`<p>
            <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#collapseWidthExample" aria-expanded="false" aria-controls="collapseWidthExample">
            ${namefromreceived}
            </button>
        </p>
        <div style="min-height: 120px;">
            <div class="collapse collapse-horizontal" id="collapseWidthExample">
            <div class="card card-body" style="width: 300px;" id="${namefromreceived}">
            </div>
            </div>
        </div>`
        unicastoutput.appendChild(newelement)
    }

    appendunicastelement(`${data.user.username}: ${data.message}`,'left',namefromreceived)
})


socket.on('receiveimagebroadcast', (image) => {
    
    const imgPreview = document.createElement('div');
    imgPreview.style.display = "block";
    imgPreview.innerHTML = '<img src="' + image + '"class = "image-message" />';
    broadcastoutput.append(imgPreview)
})

socket.on('receiveimagemulticast', (image) => {
    
    const imgPreview = document.createElement('div');
    imgPreview.style.display = "block";
    imgPreview.innerHTML = '<img src="' + image + '"class = "image-message" />';
    multicastoutput.append(imgPreview)
})

socket.on('receiveimageunicast', data => {
    const imgPreview = document.createElement('div');
    imgPreview.style.display = "block";
    imgPreview.innerHTML = '<img src="' + data.base64 + '"class = "image-message" />';
    const output_temp = document.getElementById(`${data.user.username}`)
    output_temp.append(imgPreview)
})




socket.on('old-broadcast-messages-recover',data=>{
    for(i=0;i<data.length;i++){
        if(data[i].user.username==username)
            appendelement(`You: ${data[i].message}`,'right','broadcast')
            
        else
        {
            appendelement(`${data[i].user.username}: ${data[i].message}`,'left','broadcast')
        }
    }
})

socket.on('old-multicast-messages-recover',data=>{
    for(i=0;i<data.length;i++){
        if(data[i].user.username==username)
            appendelement(`You: ${data[i].message}`,'right','multicast')
            
        else
        {
            appendelement(`${data[i].user.username}: ${data[i].message}`,'left','multicast')
        }
    }
})



