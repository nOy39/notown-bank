import store from '../store/index'

export default function (to, from, next) {
    if (store.getters.getClient) {
        next()
    } else {
        next('/?roleError=true')
    }
}
