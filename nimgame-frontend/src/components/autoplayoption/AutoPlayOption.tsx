import styles from "./AutoPlayOption.module.css";

type AutoPlayOptionProps = {
  onAutoPlayChange: (isChecked: boolean) => void;
};

export function AutoPlayOption(props: AutoPlayOptionProps) {
  return (
    <div>
      <label htmlFor="auto-play" className={styles.Label}>
        autoPlay
      </label>
      <input
        name="auto-play"
        type="checkbox"
        onChange={(e) => props.onAutoPlayChange(e.target.checked)}
      />
    </div>
  );
}
