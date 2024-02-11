import axios from 'axios';
import {useEffect, useState} from "react";

interface Category {
    id: number;
    name: string;
    description: string;
}

function App() {
    const [categories, setCategories] = useState<Category[]>([]);

    useEffect(() => {
        async function fetchCategories() {
            try {
                const response = await axios.get<Category[]>('http://localhost:8088/api/categories');
                setCategories(response.data);
            } catch (error) {
                console.error('Error fetching categories:', error);
            }
        }

        fetchCategories();
    }, []);

    return (
        <>
            <h1>List of Categories</h1>
            <ul>
                {categories.map(category => (
                    <li key={category.id}>
                        <h2>{category.name}</h2>
                        <p>{category.description}</p>
                    </li>
                ))}
            </ul>
        </>
    );
}

export default App;
