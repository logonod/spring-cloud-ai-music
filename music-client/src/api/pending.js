import axios from 'axios'

function parseJwt (token) {
    var base64Url = token.split('.')[1]
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/')
    var jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2)
    }).join(''))
  
    return JSON.parse(jsonPayload)
}

export async function getPending() {
    const token = localStorage.getItem('accessToken')
    if (token) {
        const jwt = parseJwt(token)
        const id = jwt.id
        const response = await axios.get('/api/music/pending', {
            params: { id },
            headers: {
                'Authorization': `Bearer ${token}` 
            }
        })
        response.data.list.forEach(
            (item, index, arr) => {
                if (item.ai == '周杰伦') {
                    arr[index].cover = "http://asset.liuzeyu.me/zhoujielun.png"
                } else if (item.ai == '孙燕姿') {
                    arr[index].cover = "http://asset.liuzeyu.me/sunyanzi.png"
                } else if (item.ai == '张学友') {
                    arr[index].cover = "http://asset.liuzeyu.me/zhangxueyou.png"
                }
                if (item.status == '0') {
                    arr[index].status = "干声分离中..."
                } else if (item.status == '1') {
                    arr[index].status = "AI处理中..."
                } else if (item.status == '2') {
                    arr[index].status = "后期合成中..."
                }
            }
        )
        return response.data.list
    } else {
        window.location.href = '/signin';
    }
    return []
}