import healthColor from "../utils/healthColor";

const HealthBar = () => {
    const maxHealth = 8; // PLACEHOLDER
    const health = 5; // PLACEHOLDER
    
    const color = healthColor(maxHealth, health);
    
    return (
        <div>
            <div className="text-center">
                {health} / {maxHealth}
            </div>
            <Progress
                max={maxHealth}
                value={health}
                color={color}
            />
        </div>
    );
}

export default HealthBar;