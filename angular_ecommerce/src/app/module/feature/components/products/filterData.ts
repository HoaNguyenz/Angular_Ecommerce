export const filters = [
    {
        id: "color",
        name: "Color",
        options:[
            {value: "white", label: "White"},
            {value: "black", label: "Black"},
            {value: "red", label: "Red"},
            {value: "blue", label: "Blue"},
            {value: "pink", label: "Pink"},
            {value: "green", label: "Green"},
            {value: "yellow", label: "Yellow"},
        ]
    },

    {
        id: "size",
        name: "Size",
        options: [
            {value: "9", label: "9"},
            {value: "10", label: "10"},
            {value: "11", label: "11"},
            {value: "12", label: "12"},
        ]
    }, 
];

export const singleFilter = [
    {
        id: "price",
        name: "Price",
        options: [
            {value: "0-399", label: "$0 To $399"},
            {value: "400-999", label: "$400 To $999"},
            {value: "1000-1999", label: "$1000 To $1999"},
            {value: "2000-2999", label: "$2000 To $2999"},
            {value: "3000-4999", label: "$3000 To $4999"},
        ]
    },
    {
        id: "stock",
        name: "Availability",
        options: [
            {value: "in_stock", label: "In Stock"},
            {value: "out_of_stock", label: "Out Of Stock"},
        ]
    },
]