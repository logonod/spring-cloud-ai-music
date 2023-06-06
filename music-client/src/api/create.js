import axios from 'axios'
import FormData from 'form-data'

function parseJwt (token) {
    var base64Url = token.split('.')[1]
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/')
    var jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2)
    }).join(''))
  
    return JSON.parse(jsonPayload)
}

export async function postCreate(file, title, artist, ai, onUploadProgress) {
    const token = localStorage.getItem('accessToken')
    if (token) {
        const jwt = parseJwt(token)
        const id = jwt.id

        var formData = new FormData()
        formData.append("file", file)
        formData.append('info', new Blob([JSON.stringify({
                        "user": id,
                        "title": title,
                        "artist": artist,
                        "ai": ai                
                    })], {
                        type: "application/json"
                    }))
        const response = await axios({
                        method: "post",
                        url: "/api/music/add",
                        data: formData,
                        headers: { 
                            "Content-Type": "multipart/form-data",
                            'Authorization': `Bearer ${token}`
                        },
                        onUploadProgress: onUploadProgress
                      })
                        .then(function (response) {
                          return response
                        })
                        .catch(function (response) {
                          return response
                        });
        
        return response
    } else {
        window.location.href = '/signin';
    }
    return []
}