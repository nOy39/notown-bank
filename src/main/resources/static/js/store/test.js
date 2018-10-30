
export default {
    state: {
        items:[
            {url: '1', name: 'first', someInt: 343},
            {url: '2', name: 'second', someInt: 443},
            {url: '3', name: 'third', someInt: 434},
            {url: '4', name: 'fourth', someInt: 344},
            {url: '5', name: 'fifth', someInt: 334},
            {url: '6', name: 'six', someInt: 433},
        ]
    },
    mutations: {

    },
    getters: {
        getItems(state) {
            return state.items
        }
    },
    actions: {}
}
