import axios from 'axios'

const service = axios.create({
    baseURL: '/api',
    timeout: 5000
})

service.interceptors.request.use(config => {
    const token = localStorage.getItem('token')
    if (token) {
        config.headers['token'] = token
    }
    return config
}, error => {
    return Promise.reject(error)
})

service.interceptors.response.use(response => {
    const { data } = response
    const hasCode = data && data.code !== undefined && data.code !== null
    const code = hasCode ? Number(data.code) : 200

    if (code !== 200) {
        if (code === 401) {
            localStorage.removeItem('token')
            localStorage.removeItem('userInfo')
            localStorage.removeItem('user')
            window.location.href = '/login'
        }
        return Promise.reject(new Error(data?.message || 'Error'))
    }

    return response
}, error => {
    return Promise.reject(error)
})

export default service