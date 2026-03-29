import service from '@/utils/request'

export function login(data) {
    return service.post('/user/login', data).catch(error => {
        const status = error?.response?.status
        if (status === 404 || status === 405) {
            return service.post('/auth/login', data)
        }
        throw error
    })
}

export function getCategory(token) {
    return service.get('/knowledge/category/tree', {
        headers: {
            token
        }
    })
}