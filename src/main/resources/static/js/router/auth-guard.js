import store from '../store/index'

export default function (to, from, next) {
    if (store.getters.getUser) {
        next()
    } else {
        next('/')
    }
}
