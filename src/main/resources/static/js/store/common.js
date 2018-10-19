export default {
    state: {
        test: ''
    },
    mutations: {
        SET_TEST(state, payload) {
            state.test = payload
        }
    },
    getters: {
        getTest(state) {
            return state.test
        }
    },
    actions: {
        setTest({commit}, payload) {
            commit('SET_TEST', payload)
        }
    }
}
