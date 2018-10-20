export default {
    state: {
        user: '',
        auth: false
    },
    mutations: {
        SET_USER(state, payload) {
            state.user = payload
        }
    },
    getters: {
        getUser(state) {
            return state.user
        }
    },
    actions: {
        setUser({commit}, payload){
            commit('SET_USER', payload)
        }
    }
}
